import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrossistesComponent } from './grossistes.component';

describe('GrossistesComponent', () => {
  let component: GrossistesComponent;
  let fixture: ComponentFixture<GrossistesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GrossistesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GrossistesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
