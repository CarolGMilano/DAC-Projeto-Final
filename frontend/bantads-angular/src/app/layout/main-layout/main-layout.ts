import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { Navbar } from '../../components';

@Component({
  selector: 'app-main-layout',
  imports: [
    RouterOutlet,
    Navbar
  ],
  templateUrl: './main-layout.html',
  styleUrl: './main-layout.css',
})
export class MainLayout {

}
