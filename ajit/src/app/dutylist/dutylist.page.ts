import { Component, OnInit, ViewChild } from '@angular/core';
import { DutyService } from '../service/duty.service';
import { Router } from '@angular/router';
import { FormControl } from '@angular/forms';
import { startWith, map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { CompleteTestService } from '../service/complete-test-service.service';
import { PracharakNameService } from '../service/pracharak-name.service';
import { SectorNumberService } from '../service/sector-number.service';
import { BranchNameService } from '../service/branch-name.service';
import { AutoCompleteComponent } from 'ionic4-auto-complete';

@Component({
  selector: 'app-dutylist',
  templateUrl: './dutylist.page.html',
  styleUrls: ['./dutylist.page.scss'],
  providers: [CompleteTestService]
})
export class DutylistPage implements OnInit {

  sectorNumber: string = "";

  public labelAttribute: string;
  @ViewChild('pracharakName')
  pracharakNameSearch: AutoCompleteComponent;
  @ViewChild('branchName')
  branchNameSearch: AutoCompleteComponent;
  @ViewChild('sectorNumber')
  sectorNumberSearch: AutoCompleteComponent;

  constructor(private router: Router,
    public pracharakNameService: PracharakNameService,
    public sectorNumberService: SectorNumberService,
    public branchNameService: BranchNameService) {
  }

  ngOnInit() { }

  displayDutyDetails(event) {
    // alert(this.pracharakNameSearch.getValue());
    // alert(this.sectorNumberSearch.getValue());
    // alert(this.branchNameSearch.getValue());
    // alert(this.pracharakNameSearch.getValue()+'|'+this.sectorNumberSearch.getValue()+'|'+this.branchNameSearch.getValue());
    let counter = 0;
    let screenToBeDisplayed = '';
    if (this.pracharakNameSearch.getValue() != undefined) {
      ++counter;
      screenToBeDisplayed = 'pracharak';
    }
    if (this.branchNameSearch.getValue() != undefined) {
      ++counter;
      screenToBeDisplayed = 'branch';
    }
    if (this.sectorNumber != '') {
      ++counter;
      screenToBeDisplayed = 'sector';
    }
    if (counter != 1) {
      alert('Please enter only one filter value');
      return;
    }
    this.router.navigate(['/dutyDetails'], {
      queryParams:
      {
        pracharakName: this.pracharakNameSearch.getValue(),
        sectorNumber: this.sectorNumber,
        branchName: this.branchNameSearch.getValue(),
        screenToBeDisplayed: screenToBeDisplayed
      }
    });
  }

}
