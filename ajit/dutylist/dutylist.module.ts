import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DutylistPageRoutingModule } from './dutylist-routing.module';

import { DutylistPage } from './dutylist.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DutylistPageRoutingModule
  ],
  declarations: [DutylistPage]
})
export class DutylistPageModule {}
