import { Component, OnInit } from '@angular/core';
import { SimplyCar } from '../simplycar';
import { ActivatedRoute, Router } from '@angular/router';
import { SimplyCarService } from '../simplycar.service';

@Component({
  selector: 'app-update-simplycar',
  templateUrl: './update-simplycar.component.html',
  styleUrls: ['./update-simplycar.component.css']
})
export class UpdateSimplyCarComponent implements OnInit {

  id: number;
  simplycar: SimplyCar;

  constructor(private route: ActivatedRoute,private router: Router,
    private simplycarService: SimplyCarService) { }

  ngOnInit() {
    this.simplycar = new SimplyCar();

    this.id = this.route.snapshot.params['id'];
    
    this.simplycarService.getSimplyCar(this.id)
      .subscribe(data => {
        console.log(data)
        this.simplycar = data;
      }, error => console.log(error));
  }

  updateSimplyCar() {
    this.simplycarService.updateSimplyCar(this.id, this.simplycar)
      .subscribe(data => console.log(data), error => console.log(error));
    this.simplycar = new SimplyCar();
    this.gotoList();
  }

  onSubmit() {
    this.updateSimplyCar();    
  }

  gotoList() {
    this.router.navigate(['simplycar']);
  }
}