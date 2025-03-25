import { Injectable } from '@angular/core'; 
import { HttpClient, HttpHeaders } from '@angular/common/http'; 
import { Observable } from 'rxjs'; 
import { Setting } from '../model/setting';

@Injectable({
  providedIn: 'root',
}) 
export class SettingService {
  private apiUrl = '/api/settings';

  constructor(private http: HttpClient) {}

  getAllSettings(): Observable<Setting[]> {
    return this.http.get<Setting[]>(this.apiUrl);
  }

  getSettingByKey(key: string): Observable<Setting> {
    const url = `${this.apiUrl}/${key}`;
    return this.http.get<Setting>(url);
  }

  createSetting(setting: Setting): Observable<Setting> {
    return this.http.post<Setting>(this.apiUrl, setting);
  }

  updateSetting(setting: Setting): Observable<Setting> {
    const url = `${this.apiUrl}/${setting.key}`;
    return this.http.put<Setting>(url, setting);
  }

  deleteSetting(key: string): Observable<void> {
    const url = `${this.apiUrl}/${key}`;
    return this.http.delete<void>(url);
  }
}