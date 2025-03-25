import { Component, OnInit, OnDestroy } from '@angular/core';
import { RunService } from '../../services/run.service';
import { RunItem } from '../../model/run-item';
import { Router } from '@angular/router';
import { catchError, Subscription } from 'rxjs';
import { ErrorService } from '../../services/error.service';


@Component({
  selector: 'app-run-list',
  templateUrl: './run-list.component.html',
  styleUrls: ['./run-list.component.css'],
})
export class RunListComponent implements OnInit, OnDestroy {
  runItem: any = {};
  runItems: RunItem[] = [];
  message: string = '';
  newRunItem: RunItem = { id: 0, name: '', searchQuery: '', totalResults: 0, status: '' };
  private errorSubscription!: Subscription;
  
  constructor(private runService: RunService, 
	private router: Router, 
	private errorService: ErrorService) {}

  ngOnInit(): void {
	this.errorSubscription = this.errorService.message$.subscribe(
	   data => {
	     if (data.component === this) {
	       this.message = data.message;
	     }
	   }
	 );
		
	this.runService.getRunItems().pipe(
	  catchError(this.errorService.handleError(this))
	).subscribe((data: RunItem[] | null) => {
	  if (data) {
	    this.runItems = data;
	  }
	});
  }

  createRunItem() {
    this.router.navigate(['/run']);
  }
  
  editRunItem(item: any) {
    this.router.navigate(['/run', item.id]);
  }

  deleteRunItem(item: any) {
	if (window.confirm('Are you sure you want to delete this item?')) {
	  this.runService.deleteRunItem(item.id).pipe(
	    catchError(this.errorService.handleError(this))
	  ).subscribe(() => {
	    this.runItems = this.runItems.filter(i => i !== item);
	  });
	}
  }
  
  ngOnDestroy(): void {
    if (this.errorSubscription) {
      this.errorSubscription.unsubscribe();
    }
  }
}
