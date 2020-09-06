import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { DutyService } from './service/duty.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatNativeDateModule } from '@angular/material/core';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { AutoCompleteModule } from 'ionic4-auto-complete';
import { GlobalStoreService } from './service/global-store.service';
import { PracharakNameService } from './service/pracharak-name.service';
import { SectorNumberService } from './service/sector-number.service';
import { BranchNameService } from './service/branch-name.service';

@NgModule({
  declarations: [AppComponent],
  entryComponents: [],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    MatNativeDateModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    HttpClientModule,
    AutoCompleteModule
  ],
  providers: [
    StatusBar,
    SplashScreen,
    DutyService,
    GlobalStoreService,
    PracharakNameService,
    SectorNumberService,
    BranchNameService,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
