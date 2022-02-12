import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DefaultComponent } from './components/default/default.component';
import { OtherComponent } from './components/other/other.component';
import { RestComponent } from './components/rest/rest.component';
import { ClientCallbackComponent } from './components/client-callback/client-callback.component';

@NgModule({
  declarations: [
    AppComponent,
    DefaultComponent,
    OtherComponent,
    RestComponent,
    ClientCallbackComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
