import { Router} from '@angular/router';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  // form!: FormGroup;
  // input!: string;
  // sub$!: Subscription;
  // character!: Character[];

  // constructor(private fb: FormBuilder, private router: Router, private service: SearchService) {}

  // ngOnInit(): void {
  //     this.form = this.initialiseForm();
  // }

  // initialiseForm() : FormGroup {
  //   return this.form = this.fb.group({
  //     input: new FormControl('', [Validators.required])
  //   });
  // }

  // search() {
  //   this.sub$ = this.service.getCharacter(this.form.value.input).subscribe({
  //     next: (data) => {
  //       console.log(data);
  //       this.character = data;
  //       // this.router.navigateByUrl('/suggestions');
  //     },
  //     error: (err) => {
  //       console.error(err);
  //     }
  //   });
  // }

  constructor(private router: Router) {}

  @ViewChild('input')
  input!: ElementRef;

  search() {
    const searchQuery = this.input.nativeElement.value;
    this.router.navigate(['/search/', searchQuery]);
  }


}
