import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SettingsComponent } from './components/settings/settings.component';
import { RunListComponent } from './components/run-list/run-list.component';
import { RunItemComponent } from './components/run-item/run-item.component';
import { RepoItemComponent } from './components/repo-item/repo-item.component';

const routes: Routes = [
  { path: 'run-list', component: RunListComponent },
  { path: 'settings', component: SettingsComponent },
  { path: 'run', component: RunItemComponent },
  { path: 'run/:id', component: RunItemComponent },
  { path: 'repo/:id', component: RepoItemComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
