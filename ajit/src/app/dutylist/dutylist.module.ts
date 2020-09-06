import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { DutylistPageRoutingModule } from './dutylist-routing.module';

import { DutylistPage } from './dutylist.page';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { AutoCompleteModule } from 'ionic4-auto-complete';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    DutylistPageRoutingModule,
    MatAutocompleteModule,    
    AutoCompleteModule
  ],
  declarations: [DutylistPage]
})
export class DutylistPageModule {}
