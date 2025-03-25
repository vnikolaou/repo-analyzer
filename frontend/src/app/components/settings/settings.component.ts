import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Setting } from 'src/app/model/setting';
import { SettingService } from 'src/app/services/setting.service';
import { ErrorService } from '../../services/error.service';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'aapp-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css'],
})
export class SettingsComponent implements OnInit, OnDestroy {
  settings: Setting[] = [];
  message: string = '';
  messageType: 'success' | 'error' = 'success';
  private errorSubscription!: Subscription;

  constructor(
    private settingService: SettingService,
    private errorService: ErrorService
  ) {}

  ngOnInit(): void {
	this.errorSubscription = this.errorService.message$.subscribe(
	   data => {
	     if (data.component === this) {
	       this.message = data.message;
	     }
	   }
	 );

    this.settingService.getAllSettings().pipe(
      catchError(this.errorService.handleError(this))
    ).subscribe((data: Setting[] | null) => {
      if (data) {
        this.settings = data;
      }
    });
  }

  updateSetting(setting: any) {
    this.settingService.updateSetting(setting).pipe(
      catchError(this.errorService.handleError(this))
    ).subscribe(
      (data: Setting | null) => {
        if (data) {
          this.message = `Setting ${setting.key} updated successfully!`;
          this.messageType = 'success';
        } else {
          this.message = `Failed to update setting ${setting.key}.`;
          this.messageType = 'error';
        }
      }
    );
  }

  ngOnDestroy(): void {
    if (this.errorSubscription) {
      this.errorSubscription.unsubscribe();
    }
  }
}