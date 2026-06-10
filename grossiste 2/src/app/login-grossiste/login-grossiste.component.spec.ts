import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginGrossisteComponent } from './login-grossiste.component';

describe('LoginGrossisteComponent', () => {
  let component: LoginGrossisteComponent;
  let fixture: ComponentFixture<LoginGrossisteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginGrossisteComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LoginGrossisteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
