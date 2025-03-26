import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SettingsComponent } from './components/settings/settings.component';
import { RunListComponent } from './components/run-list/run-list.component';
import { RunItemComponent } from './components/run-item/run-item.component';
import { RepoItemComponent } from './components/repo-item/repo-item.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'; 


@NgModule({
  declarations: [
    AppComponent,
	SettingsComponent,
	RunListComponent,
	RunItemComponent,
	RepoItemComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
	HttpClientModule,
	FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
