import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginPharmacieComponent } from './login-pharmacie.component';

describe('LoginPharmacieComponent', () => {
  let component: LoginPharmacieComponent;
  let fixture: ComponentFixture<LoginPharmacieComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginPharmacieComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LoginPharmacieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
