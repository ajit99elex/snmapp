import { Component, OnInit } from '@angular/core';
import { DutyService } from '../service/duty.service';
import { FormGroup } from "@angular/forms";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.page.html',
  styleUrls: ['./admin.page.scss'],
})
export class AdminPage implements OnInit {

  constructor(private dataService: DutyService) { }

  ngOnInit() {
  }

  uploadFile(event){
    this.dataService.uploadDutyDetailsFile(event.target.files[0]).subscribe(
      (data) => {
        console.log(data);
      }
    );
  }

}
