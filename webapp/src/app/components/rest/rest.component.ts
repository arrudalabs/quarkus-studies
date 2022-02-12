import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { take } from 'rxjs/operators';

@Component({
  selector: 'app-rest',
  templateUrl: './rest.component.html',
  styleUrls: ['./rest.component.css'],
})
export class RestComponent implements OnInit {
  msg$: Subject<string> = new BehaviorSubject<string>('');

  constructor(private _http: HttpClient) {}

  ngOnInit(): void {
    this._http
      .get('/resources/hello', { responseType: 'text' })
      .pipe(take(1))
      .subscribe({
        next: (msg) => {
          return this.msg$.next(msg);
        },
        error: (err) => {
          console.error(err);
          this.msg$.next('An error happened - see the console log for details');
        },
      });
  }
}
