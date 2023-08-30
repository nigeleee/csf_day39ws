import { SearchService } from './../service/search.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-character',
  templateUrl: './character.component.html',
  styleUrls: ['./character.component.css']
})
export class CharacterComponent implements OnInit{

  constructor(private service: SearchService, private router: ActivatedRoute, private fb: FormBuilder){}

  character!: any;
  sub$!: Subscription
  id: string = '';

  form!: FormGroup;
  input!: any;


  ngOnInit(): void {

    this.id = this.router.snapshot.params['id'];

    this.sub$= this.service.getCharacterById(this.id).subscribe({
      next : (data) => {
        this.character = data;
      }
    });

    this.form = this.initialiseForm();

  }

  initialiseForm() : FormGroup {
    return this.form = this.fb.group({
    input: new FormControl('')
  });
}

  post() {

    // const payload = {
    //   text: this.form.value.input
    // };

    this.service.postComment(this.id, this.form.value.input).subscribe({
      next: (data) => {
        alert("Comment successfully posted");
        this.form.reset();
      },
      error: (err) => {
        console.log(err);
      }
    })
  }

}


