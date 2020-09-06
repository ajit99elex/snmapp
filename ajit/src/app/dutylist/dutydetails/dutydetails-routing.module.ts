import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DutydetailsPage } from './dutydetails.page';

const routes: Routes = [
  {
    path: '',
    component: DutydetailsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DutydetailsPageRoutingModule {}
