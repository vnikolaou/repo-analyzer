import { Injectable, NgZone } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SseService {
  private eventSource: EventSource | null = null;	
  private eventSubject: Subject<any> = new Subject<any>();
  
  constructor(private zone: NgZone) {}

  connect(url: string): Observable<any> {
	if (!this.eventSource) {
	  this.eventSource = new EventSource(url);
	  this.eventSource.onmessage = (event) => {
		this.zone.run(() => {
		  this.eventSubject.next(JSON.parse(event.data));
		});
	  };
	  this.eventSource.onerror = (error) => {
		this.zone.run(() => {
		  this.eventSubject.error(error);
		});
	  };
	}
	return this.eventSubject.asObservable();
  }
  
  disconnect(): void {
    if (this.eventSource) {
      this.eventSource.close();
      this.eventSource = null;
    }
  }
}
