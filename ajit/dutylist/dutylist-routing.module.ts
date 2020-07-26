import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { DutylistPage } from './dutylist.page';

const routes: Routes = [
  {
    path: '',
    component: DutylistPage
  },
  {
    path: '/dutyDetails',
    loadChildren: () => import('./dutydetails/dutydetails.module').then( m => m.DutydetailsPageModule)
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DutylistPageRoutingModule {}
