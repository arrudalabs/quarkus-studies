import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-default',
  templateUrl: './default.component.html',
  styleUrls: ['./default.component.css']
})
export class DefaultComponent implements OnInit {

  externalUrl = '/servlet/make-external-call';

  constructor() {
    if (window.location.port === "4200") {
      this.externalUrl = "http://localhost:8080" + this.externalUrl;
    }
  }

  ngOnInit(): void {
  }

}
