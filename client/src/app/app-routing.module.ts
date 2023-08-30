import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './component/home.component';
import { CharacterComponent } from './component/character.component';
import { SearchComponent } from './component/search.component';
import { CommentsComponent } from './component/comments.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: '/home'},
  { path: 'home', component: HomeComponent, title: 'Home'},
  { path: 'search/:name', component: SearchComponent, title: 'Select Your Query'},
  { path: 'character/:id', component: CharacterComponent, title: 'Search Results'},
  { path: 'character/:id/comments', component: CommentsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
