import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockGrossisteComponent } from './stock-grossiste.component';

describe('StockGrossisteComponent', () => {
  let component: StockGrossisteComponent;
  let fixture: ComponentFixture<StockGrossisteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StockGrossisteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StockGrossisteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});