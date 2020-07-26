import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { DutydetailsPage } from './dutydetails.page';

describe('DutydetailsPage', () => {
  let component: DutydetailsPage;
  let fixture: ComponentFixture<DutydetailsPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DutydetailsPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(DutydetailsPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
