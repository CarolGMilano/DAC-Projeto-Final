import { Component } from '@angular/core';
import { GerenteService } from '../../../services';
import { IGerente } from '../../../shared';
import { MoedaBrPipe } from '../../../shared/pipes/moedaBr/moeda-br-pipe';

@Component({
  selector: 'app-administrator-dashboard',
  imports: [MoedaBrPipe],
  templateUrl: './administrator-dashboard.html',
  styleUrl: './administrator-dashboard.css',
})
export class AdministratorDashboard {
gerentes: IGerente[] = [];

constructor(private gerenteService: GerenteService) {}

  ngOnInit() {
    //this.gerentes = this.gerenteService.get();
    console.log(this.gerentes);
  }

}
