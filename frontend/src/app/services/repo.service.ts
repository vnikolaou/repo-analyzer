import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'; 
import { Observable } from 'rxjs'; 
import { Repository } from '../model/repository';

@Injectable({
  providedIn: 'root',
}) 
export class RepoService {
  private apiUrl = '/api/repos';
  
  constructor(private http: HttpClient) {}

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
  
}