import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  
  constructor(private http: HttpClient) { }

  searchCustomer(customerData: any): Observable<any> {
    return this.http.post('/api/customer/get', customerData);
  }

  getAll(): Observable<any> {
  
    return this.http.get('/api/customer/getAll');
  }

  createCustomer(customerData: any): Observable<any> {
    return this.http.post('/api/customer/create', customerData);
  }

  updateCustomer(sharedKey: any, customerData: any): Observable<any> {
    console.log(sharedKey, customerData)
    return this.http.put(`/api/customer/update/${sharedKey}`, customerData);
  }

  deleteCustomer(sharedKey: any): Observable<any> {
    return this.http.delete(`/api/customer/delete/${sharedKey}`);
  }

}
