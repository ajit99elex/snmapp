import { Component, OnInit } from '@angular/core';
import { DutyService } from 'src/app/service/duty.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
})
export class HomePage implements OnInit {

  thought: string = '';

  constructor(private dataService: DutyService) { }

  ngOnInit() {
    this.dataService.fetchThoughtOfTheDay().subscribe(
      (data) => {
        this.thought = data;
      }
    );    
  }

}
