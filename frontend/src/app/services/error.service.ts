import { Injectable } from '@angular/core';
import { of, BehaviorSubject, throwError, Subject } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';


@Injectable({
	providedIn: 'root',
})
export class ErrorService {
	private messageSubject = new Subject<{ component: any, message: string }>();
	message$ = this.messageSubject.asObservable();
	
  handleError = (component: any) => (error: HttpErrorResponse) => {
    let errorMessage = `Error: ${error.error.message || error.error.error}`; // .message from generic server/client error & .error from specific server github error
    this.messageSubject.next({ component, message: errorMessage });
	
    return throwError(() => new Error(errorMessage));
  }
  
  
}