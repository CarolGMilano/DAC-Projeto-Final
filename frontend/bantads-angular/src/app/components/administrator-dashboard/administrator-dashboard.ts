import { Component } from '@angular/core';
import { GerenteService } from '../../services/gerente/gerente';
import { Gerente } from '../../shared';
import { MoedaBrPipe } from '../../shared/pipes/moedaBr/moeda-br-pipe';

@Component({
  selector: 'app-administrator-dashboard',
  imports: [MoedaBrPipe],
  templateUrl: './administrator-dashboard.html',
  styleUrl: './administrator-dashboard.css',
})
export class AdministratorDashboard {
gerentes!: Gerente[];

constructor(private gerenteService: GerenteService) {}

  ngOnInit() {
    this.gerentes = this.gerenteService.get();
    console.log(this.gerentes);
  }

}
