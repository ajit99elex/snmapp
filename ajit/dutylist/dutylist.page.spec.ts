import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { DutylistPage } from './dutylist.page';

describe('DutylistPage', () => {
  let component: DutylistPage;
  let fixture: ComponentFixture<DutylistPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DutylistPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(DutylistPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
