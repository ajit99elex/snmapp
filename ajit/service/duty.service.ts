import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Appconfig } from '../dto/appconfig';
import { retry, catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DutyService {

  constructor(private httpClient: HttpClient) { }

  fetchPracharakDutyDetails(pracharakName: string){
    return this.httpClient.get(Appconfig.dutyListService+'/fetchDutyListForPracharak?'
      +'pracharakName='+pracharakName)
      .pipe(
        retry(1),
        catchError(this.handleError)
      );
  }

  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
