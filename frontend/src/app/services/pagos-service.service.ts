import { HttpClient } from '@angular/common/http';
import { ServicioInter } from '../interface/servicioInter';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const URL_API='https://uala-no-country.onrender.com/v1/api/service/payment/'
let balance : number =0;
@Injectable({
  providedIn: 'root'
})


export class PagosServiceService {
 
  private _envio: String[]=[];
  private empresa:String="";
  constructor(private http: HttpClient) { }

  findService(modelo:any){
    return this.http.post<ServicioInter>(`${URL_API}calculate/bill`, modelo);
  }

  agregardato(dato:String, dato2:String){
    this._envio=[];
    this._envio.push(dato);
    this._envio.push(dato2);
  }

  get_Envio(){
    return this._envio;
  }
  definirEmpresa(dato: String){
    this.empresa=dato;
  }
  get_empresa(){
    return this.empresa;
  }

  saveServicePayment(servicio : ServicioInter): Observable<any>{
    return this.http.post<ServicioInter>(`${URL_API}confirm/service`, servicio, {observe: 'response'});
  }

  findbalance(){
    return this.http.get<number>(`${URL_API}calculate/balance`)
  }

  saveRechargeService(modelo:any){
    return this.http.post<any>(`${URL_API}confirm/recharge`, modelo, {observe: 'response'});
  }

  findHistory(){
    return this.http.get<any>(`${URL_API}history/payments`)
  }
  

}
