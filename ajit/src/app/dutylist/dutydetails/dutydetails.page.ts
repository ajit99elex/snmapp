import { Component, OnInit } from '@angular/core';
import { DutyService } from 'src/app/service/duty.service';
import { ActivatedRoute } from '@angular/router';
import { DutyDetails } from 'src/app/dto/dutyDetails';

@Component({
  selector: 'app-dutydetails',
  templateUrl: './dutydetails.page.html',
  styleUrls: ['./dutydetails.page.scss'],
})
export class DutydetailsPage implements OnInit {

  pracharakName: string;
  sectorNumber: string;
  branchName: string;
  dutyDetails: DutyDetails[] = [];
  screenToBeDisplayed: string = '';

  constructor(private dutyService: DutyService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams.subscribe(
      params => {
        this.pracharakName = params['pracharakName'] == undefined ? '' : params['pracharakName'];
        this.sectorNumber = params['sectorNumber'] == undefined ? '' : params['sectorNumber'];
        this.branchName = params['branchName'] == undefined ? '' : params['branchName'];
        this.screenToBeDisplayed = params['screenToBeDisplayed'];
        this.dutyService.fetchPracharakDutyDetails(this.pracharakName, this.sectorNumber, this.branchName).subscribe(
          (data) => {
            data.forEach(
              (dutyInfo) => {
                if(dutyInfo.dutydate != undefined && dutyInfo.dutydate != '' && dutyInfo.dutydate != null){
                  this.dutyDetails.push(dutyInfo);
                }
              }
            );
            console.log(this.dutyDetails);
          }
        );
      }
    );
  }



}
