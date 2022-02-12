import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientCallbackComponent } from './client-callback.component';

describe('ClientCallbackComponent', () => {
  let component: ClientCallbackComponent;
  let fixture: ComponentFixture<ClientCallbackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientCallbackComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientCallbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
