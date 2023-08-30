import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  url: string = "http://localhost:8080/api/search"

  getCharacter(name: string) : Observable<any> {

    const params = new HttpParams()
      .set('name', name)

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')

    return this.http.get(this.url, {params, headers});
  }

  getCharacterById(id: string) :Observable<any> {

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')

    return this.http.get(this.url + `/${id}`);
  }

  postComment(id: string, comment: any) : Observable<any> {
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')

    return this.http.post(this.url + `/${id}`, comment, {headers});
  }

}
