import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { RepoService } from 'src/app/services/repo.service';
import { catchError, of } from 'rxjs';
import { ErrorService } from 'src/app/services/error.service';
import { Subscription } from 'rxjs';
import { Repository } from 'src/app/model/repository';

@Component({
  selector: 'app-repo-item',
  templateUrl: './repo-item.component.html',
  styleUrls: ['./repo-item.component.css']
})
export class RepoItemComponent implements OnInit {
  message: string = '';
  success: string = '';
  
  private id!: number;
  private rid!: number;
  
  copied = false;
  
  private errorSubscription!: Subscription;

  repo!: Repository;
  
  constructor(
    private repoService: RepoService,
    private router: Router,
    private route: ActivatedRoute,
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
		
	const id = this.route.snapshot.paramMap.get('id');
	if(id) this.id = +id;
	
	const rid = this.route.snapshot.queryParamMap.get('rid');
	if(rid) this.rid = +rid;
	
    if (id) {
      this.repoService.getRepo(this.id).pipe(
        catchError(this.errorService.handleError(this))
      ).subscribe(
        (data: Repository | null) => {
          if (data) {
            this.repo = data;
          }
        }
      );
    }
  }

  updateRepoItem(form: NgForm) {	
    if (form.valid) {
      const saveObservable = this.repoService.updateRepo(this.repo);

      saveObservable.pipe(
        catchError(this.errorService.handleError(this))
      ).subscribe(
        () => {
			this.success = "The repository was succesfully updated.";
		//  this.router.navigate(['/run', this.rid]);
        }
      );
    }
  }

  copyToClipboard() {
    navigator.clipboard.writeText(this.repo.cloneUrl).then(() => {
      this.copied = true;
      setTimeout(() => (this.copied = false), 2000); // Reset after 2 sec
    });
  }
  
  cancel() {
    this.router.navigate(['/run', this.rid]);
  }

  ngOnDestroy(): void {
    if (this.errorSubscription) {
      this.errorSubscription.unsubscribe();
    }
  }
  
}