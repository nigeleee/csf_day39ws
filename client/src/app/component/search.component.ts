import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SearchService } from '../service/search.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit{

  constructor(private router: ActivatedRoute, private service: SearchService) {}

  sub$!: Subscription;
  name: string = '';
  results: any[] = [];

  ngOnInit(): void {
    this.name = this.router.snapshot.params['name'];

    this.sub$ = this.service.getCharacter(this.name).subscribe({
      next: (data) => {
        console.log(data);
        this.results = data;
        // this.router.navigateByUrl('/suggestions');
      },
      error: (err) => {
        console.error(err);
      }
    });
  }

}
