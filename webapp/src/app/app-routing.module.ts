import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DefaultComponent } from './components/default/default.component';
import { OtherComponent } from './components/other/other.component';
import { RestComponent } from './components/rest/rest.component';
import { ClientCallbackComponent } from './components/client-callback/client-callback.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', component: DefaultComponent },
  { path: 'other', component: OtherComponent },
  { path: 'rest', component: RestComponent },
  { path: 'clientCallback', component: ClientCallbackComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
