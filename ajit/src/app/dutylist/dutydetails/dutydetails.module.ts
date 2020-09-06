import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DutydetailsPageRoutingModule } from './dutydetails-routing.module';

import { DutydetailsPage } from './dutydetails.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DutydetailsPageRoutingModule
  ],
  declarations: [DutydetailsPage]
})
export class DutydetailsPageModule {}
