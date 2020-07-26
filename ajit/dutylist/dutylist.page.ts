import { Component, OnInit } from '@angular/core';
import { DutyService } from '../service/duty.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dutylist',
  templateUrl: './dutylist.page.html',
  styleUrls: ['./dutylist.page.scss'],
})
export class DutylistPage implements OnInit {

  constructor(private router: Router) { }

  pracharakName: string;
  sectorNumber: string;

  ngOnInit() {
  }

  displayDutyDetails(){
    alert('displayDutyDetails');
    this.router.navigate(['/dutyDetails'], {queryParams: {pracharakName: this.pracharakName, sectorNumber: this.sectorNumber}});
  }



}
