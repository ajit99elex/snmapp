import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Appconfig } from '../dto/appconfig';
import { retry, catchError, tap, map } from 'rxjs/operators';
import { throwError, Observable } from 'rxjs';
import { DutyDetails } from '../dto/dutyDetails';

@Injectable({
  providedIn: 'root'
})
export class DutyService {

  constructor(private httpClient: HttpClient) { }

  fetchPracharakDutyDetails(pracharakName: string, sectorNumber: string, branchName: string) : Observable<DutyDetails[]>{
    return this.httpClient.get<DutyDetails[]>(Appconfig.dataService+'/fetchDutyListForPracharak?'
      +'pracharakName='+pracharakName
      +'&sectorNumber='+sectorNumber
      +'&branchName='+branchName)
      .pipe(
        // map(data => data as DutyDetails[]),
        // tap(
        //   data => {return data}
        // ),        
        catchError(this.handleError)
      );
  }

  fetchThoughtOfTheDay() : Observable<string>{
    return this.httpClient.get<string>(Appconfig.dataService+'/fetchThoughtOfTheDay').pipe();
  }

  uploadDutyDetailsFile(file){
    //var headers = new HttpHeaders({'Content-type':'multipart/mixed'});
    return this.httpClient.post(Appconfig.dataService+'/uploadDutyDetailsFile',file)
      .pipe(
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
