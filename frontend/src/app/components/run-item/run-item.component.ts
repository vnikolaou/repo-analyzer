import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RunService } from '../../services/run.service';
import { SettingService } from '../../services/setting.service';
import { RunItem } from '../../model/run-item';
import { NgForm } from '@angular/forms';
import { RepoService } from 'src/app/services/repo.service';
import { catchError, of } from 'rxjs';
import { ErrorService } from 'src/app/services/error.service';
import { Subscription } from 'rxjs';
import { HttpEventType } from '@angular/common/http';
import { Repository } from 'src/app/model/repository';

@Component({
  selector: 'app-run-item',
  templateUrl: './run-item.component.html',
  styleUrls: ['./run-item.component.css']
})
export class RunItemComponent implements OnInit {
  runItem: RunItem = { id: 0, name: '', searchQuery: '', totalResults: 0, status: '' };
  isEditMode: boolean = false;
  isQueried: boolean = false;
  isSelectionStarted: boolean = false;
  isInitialized: boolean = false;
  isAnalysisStarted: boolean = false;
  isSaved: boolean = false;
  isValidSize: boolean = false;
  message: string = '';
  showCloneProgress: boolean = false;
  maxLimit: number = 0;
  private errorSubscription!: Subscription;

  repos: Repository[] = [];
  
  constructor(
    private runService: RunService,
    private repoService: RepoService,
    private settingService: SettingService,
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
    if (id) {
      this.isEditMode = true;
      this.isSaved = true;
      this.runService.getRunItemById(+id).pipe(
        catchError(this.errorService.handleError(this))
      ).subscribe(
        (data: RunItem | null) => {
          if (data) {
			if(data.status == 'INITIALIZED') {
				this.isInitialized = true;
			}			
			if(data.status == 'SELECTION_STARTED') {
				this.isSelectionStarted = true;
			}
			if(data.status == 'ANALYSIS_STARTED') {
				this.isAnalysisStarted = true;
			}
            this.runItem = data;
            this.loadMaxLimit();
			this.getRepos();
          }
        }
      );
    }
  }

  saveRunItem(form: NgForm) {
    if (form.valid) {
      this.isQueried = false;
      this.isSaved = true;

      const saveObservable = this.runItem.id
        ? this.runService.updateRunItem(this.runItem)
        : this.runService.createRunItem(this.runItem);

      saveObservable.pipe(
        catchError(this.errorService.handleError(this))
      ).subscribe(
        () => {
          this.router.navigate(['/run-list']);
        }
      );
    }
  }

  cancel() {
    this.router.navigate(['/run-list']);
  }

  queryTotalResults() {
	this.isLoading = true; 
    this.repoService.getSearchTotalResults(this.runItem.searchQuery).pipe(
      catchError(this.errorService.handleError(this))
    ).subscribe(
      (data: number | null) => {
        if (data !== null) {
          this.runItem.totalResults = data;
          this.message = '';
          this.isQueried = true;
          this.isValidSize = this.runItem.totalResults <= this.maxLimit;
          if (!this.isValidSize) {
            this.message = `Total results exceed the maximum limit of ${this.maxLimit}. Cloning is not allowed.`;
          }
        }
		this.isLoading = false;
      }
    );
  }

  isLoading: boolean = false;
  
  initCloning() {
    this.runService.initCloning(this.runItem).pipe(
      catchError(this.errorService.handleError(this))
    ).subscribe(
      response => {
        console.log('Init cloning successful', response);
        this.showCloneProgress = true;
		this.isLoading = true; 
		this.fetchRepos(); 

		this.runService.getRunItemById(this.runItem.id).pipe(
		        catchError(this.errorService.handleError(this))
		      ).subscribe(
		        (data: RunItem | null) => {
		          if (data) {
					if(data.status != 'INITIALIZED') {
						this.isInitialized = false;
						this.isSelectionStarted = true;
					}
		            this.runItem = data;
	
		          }
		        }
		      );
      }
    );
  }
  
  // Check if the current repository belongs to a new year group
  isNewYear(index: number): boolean {
    if (index === 0) return true; // First item, always show the header
	let currYear: number = +this.repos[index].createdAt.substring(0, 4);
	let prevYear: number = +this.repos[index - 1].createdAt.substring(0, 4);
    return currYear != prevYear;
  }
  
  showGroupInfo(repo: Repository): string {
	let currYear: number = +repo.createdAt.substring(0, 4);
    let reposInGroup = this.repos.filter(r => currYear === +r.createdAt.substring(0, 4)).length;
	
	return `Year: ${currYear} - Total repositories: ${reposInGroup}`;
  }
  
  getSample() {
	if (window.confirm('Are you sure you want to get a sample? This is not a reversible action.')) {
		this.isLoading = true; 
		this.repoService.getSample(this.runItem.id).pipe(
		  catchError(this.errorService.handleError(this))
		).subscribe(
		  response => {
		    console.log('Getting sample successful', response);
		   // this.showCloneProgress = true;
		   	this.getRepos();
			
			this.runService.getRunItemById(this.runItem.id).pipe(
				        catchError(this.errorService.handleError(this))
				      ).subscribe(
				        (data: RunItem | null) => {
				          if (data) {
							if(data.status != 'SELECTION_STARTED') {
								this.isInitialized = false;
								this.isSelectionStarted = false;
								this.isAnalysisStarted = true;
							}
				            this.runItem = data;

				          }
				        }
				      );
					  							
			this.isLoading = false; 
		  }
		);	
	}

  }
  
  finalize() {
	
  }
  
  fetchRepos() {
    this.repoService.fetchRepos(this.runItem.id, this.runItem.searchQuery).pipe(
      catchError(this.errorService.handleError(this))
    ).subscribe(
      response => {
		this.repos = response;
		this.runItem.totalResults = this.repos.length;
        console.log(response);
		this.isLoading = false; 
      }
    );
  }
  
  editRepo(item: any) {
    this.router.navigate(['/repo', item.id], { queryParams: { rid: this.runItem.id } });
  }
  
  getRepos() {
    this.repoService.getRepos(this.runItem.id).pipe(
      catchError(this.errorService.handleError(this))
    ).subscribe(
      response => {
  	this.repos = response;
        console.log(response);
  	this.isLoading = false; 
	this.runItem.totalResults = this.repos.length;
      }
    );
  }
 
  private loadMaxLimit() {
    this.settingService.getSettingByKey('MAX_LIMIT').pipe(
      catchError(this.errorService.handleError(this))
    ).subscribe(
      (data: { value: string } | null) => {
        if (data) {
          this.maxLimit = +data.value;
        }
      }
    );
  }

  scrollTo(elementId: string) {
    const element = document.getElementById(elementId);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    }
  }
  
  getRowClass(repo: Repository) {
    if(repo.analyzed) {
		return !repo.failed ? 'success' : 'failure';
	}
	return null;
  }
  
  ngOnDestroy(): void {
    if (this.errorSubscription) {
      this.errorSubscription.unsubscribe();
    }
  }
  
}