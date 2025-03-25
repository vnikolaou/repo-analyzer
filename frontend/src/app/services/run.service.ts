import { Injectable } from '@angular/core'; 
import { HttpClient } from '@angular/common/http'; 
import { Observable } from 'rxjs'; 
import { RunItem } from '../model/run-item';

@Injectable({
  providedIn: 'root',
}) 
export class RunService {
  private apiUrl = '/api/run-items';

  constructor(private http: HttpClient) {}

  getRunItems(): Observable<RunItem[]> {
    return this.http.get<RunItem[]>(this.apiUrl);
  }

  createRunItem(runItem: RunItem): Observable<RunItem> {
    return this.http.post<RunItem>(this.apiUrl, runItem);
  }

  getRunItemById(id: number): Observable<RunItem> {
    return this.http.get<RunItem>(`${this.apiUrl}/${id}`);
  }

  updateRunItem(runItem: RunItem): Observable<RunItem> {
    return this.http.put<RunItem>(`${this.apiUrl}/${runItem.id}`, runItem);
  }

  deleteRunItem(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  initCloning(runItem: any): Observable<RunItem> {
    return this.http.post<RunItem>(`${this.apiUrl}/init-cloning`, runItem);
  }
}