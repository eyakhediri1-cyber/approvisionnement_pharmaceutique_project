import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsCommandesComponent } from './details-commandes.component';

describe('DetailsCommandesComponent', () => {
  let component: DetailsCommandesComponent;
  let fixture: ComponentFixture<DetailsCommandesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailsCommandesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DetailsCommandesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
