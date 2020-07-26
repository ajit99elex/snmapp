import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dutylist',
    pathMatch: 'full'
  },
  {
    path: 'folder/:id',
    loadChildren: () => import('./folder/folder.module').then( m => m.FolderPageModule)
  },
  {
    path: 'dutylist',
    loadChildren: () => import('./dutylist/dutylist.module').then( m => m.DutylistPageModule)
  },
  {
    path: 'dutyDetails',
    loadChildren: () => import('./dutylist/dutydetails/dutydetails.module').then( m => m.DutydetailsPageModule)
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
