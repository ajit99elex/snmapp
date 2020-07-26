import { Component, OnInit } from '@angular/core';
import { DutyService } from 'src/app/service/duty.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-dutydetails',
  templateUrl: './dutydetails.page.html',
  styleUrls: ['./dutydetails.page.scss'],
})
export class DutydetailsPage implements OnInit {

  pracharakName: string;
  sectorNumber: string;

  constructor(private dutyService: DutyService,
    private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.queryParams.subscribe(
      params => {
        this.pracharakName = params['pracharakName'];
        this.sectorNumber = params['sectorNumber'];
        this.dutyService.fetchPracharakDutyDetails(this.pracharakName).subscribe(
          (data) => {
            console.log(data);
          }
        );
      }
    );
  }



}
