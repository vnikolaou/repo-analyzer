import { Injectable, NgZone } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 
import { Observable, Subject } from 'rxjs'; 
import { Repository } from '../model/repository';

@Injectable({
  providedIn: 'root',
}) 
export class RepoService {
  private apiUrl = '/api/repos';
  
  private eventSource: EventSource | null = null;	
  private eventSubject: Subject<any> = new Subject<any>();
  
  constructor(private http: HttpClient, private zone: NgZone) {}

  getSearchTotalResults(searchQuery: string): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/total-results`, {
      params: { q: searchQuery }
    });
  }

  fetchRepos(id: number, searchQuery: string): Observable<Repository[]> {
    return this.http.get<any>(`${this.apiUrl}/fetch-repos`, {
      params: { 
		id,
		q: searchQuery }
    });
  }
  
  getRepos(id: number): Observable<Repository[]> {
    return this.http.get<Repository[]>(`${this.apiUrl}`, {
      params: { 
  	id}
    });
  }
  
  getSample(id: number): Observable<Repository[]> {
    return this.http.get<Repository[]>(`${this.apiUrl}/sample`, {
      params: { 
  	id}
    });
  }
  
  updateRepo(repo: Repository): Observable<Repository> {
      return this.http.put<Repository>(`${this.apiUrl}/${repo.id}`, repo);
    }
  
  getRepo(id: number): Observable<Repository> {
    return this.http.get<Repository>(`${this.apiUrl}/${id}`);
  }
  
  connect(query: string): Observable<any> {
	  if (!this.eventSource) {
	    this.eventSource = new EventSource(this.apiUrl + "/fetch-repos?q=" + query);
	    this.eventSource.onmessage = (event) => {
	  	this.zone.run(() => {
	  	  this.eventSubject.next(JSON.parse(event.data));
		  this.eventSubject.next(event.data);
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