import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CustomerService } from './customer.service';
import { ConfirmationService, MessageService, PrimeIcons } from 'primeng/api';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.css'],
  providers: [ConfirmationService,MessageService] 
})
export class CustomerComponent implements OnInit {

  titlePage: string = 'Clientes';
  customerForm!: FormGroup;
  customers: any[] = [];
  isEditMode: boolean = false;
  isConsultMode: boolean = false;
  currentCustomer: any;
  display: boolean = false;

  constructor(
    private customerService: CustomerService,
    private fb: FormBuilder,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
  ) { }

  ngOnInit(): void {
    this.loadCustomer();
    this.customerForm = this.fb.group({
      idBusiness: [ '', this.isConsultMode ? null : Validators.required ],
      phone: [ '', this.isConsultMode ? null : Validators.required ],
      email: [ '', this.isConsultMode ? null : [Validators.required, Validators.email]],
      startDate: [ '', this.isConsultMode ? null : Validators.required ], 
      endDate: [ '', this.isConsultMode ? null : Validators.required ], 
  });
    
  }

  loadCustomer(): void {
    this.customerService.getAll().subscribe(data => {
      this.customers = data.listData;
    });
  }

  searchCustomer(): void {
      const customerData = this.customerForm.value; 
      this.customerService.searchCustomer(customerData).subscribe({
        next: (response) => {
          if(response.code == 200){
            this.customers = response.listData;
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Cliente obtenido correctamente' });
            this.customerForm.reset();
          }else{
            this.messageService.add({ severity: 'error', summary: 'Error', detail: response.message });
          }
        },
        error: (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Fallo al encontrar el cliente' });
          console.error(error);
        }
      });
      this.closeDialog();
  }

  createCustomer(): void {
    if (this.customerForm.valid) {
      const customerData = this.customerForm.value; 
      this.customerService.createCustomer(customerData).subscribe({
        next: (response) => {
          if(response.code == 200){
            this.loadCustomer();
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Cliente creado correctamente' });
            this.customerForm.reset();
          }else{
            this.messageService.add({ severity: 'error', summary: 'Error', detail: response.message });
          }
        },
        error: (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error al crear un cliente' });
          console.error(error);
        }
      });
      this.closeDialog();
      
    } else {
      this.messageService.add({ severity: 'warn', summary: 'Warning', detail: 'Porfavor ingrese todo los datos de manera correcta' });
    }
  }

  updateCustomer(sharedKey: any): void {
    if (this.customerForm.valid) {
      const customerData = this.customerForm.value; 
      this.customerService.updateCustomer(sharedKey, customerData).subscribe({
        next: (response) => {
          if(response.code == 200){
            this.loadCustomer();
            this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Cliente actualizado correctamente' });
            this.customerForm.reset();
          }else{
            this.messageService.add({ severity: 'error', summary: 'Error', detail: response.message });
          }
        },
        error: (error) => {
          this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Error al actualizar un cliente' });
          console.error(error);
        }
      });
      this.closeDialog();
      
    } else {
      this.messageService.add({ severity: 'warn', summary: 'Warning', detail: 'Porfavor ingrese todo los datos de manera correcta' });
    }
  }

  
  deleteCustomer(customer: any) {
    this.confirmationService.confirm({
      message: `¿Estás seguro de que deseas eliminar al cliente ${customer.sharedKey}?`,
      header: 'Confirmar Eliminación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.customerService.deleteCustomer(customer.sharedKey).subscribe({
          next: (response) => {
            if(response.code == 200){
              this.messageService.add({ severity: 'success', summary: 'Éxito', detail: 'Cliente eliminado correctamente' });
              this.loadCustomer();
            }else{
              this.messageService.add({ severity: 'error', summary: 'Error', detail: response.message });
            }
          },
          error: (err) => {
            this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Hubo un problema al eliminar el cliente' });
          }
        });
      },
      reject: () => {
        this.messageService.add({ severity: 'info', summary: 'Cancelado', detail: 'Eliminación cancelada' });
      }
    });
  }


  showDialog(customer?: any, mode: 'create' | 'edit' | 'consult' = 'create'): void {
    this.display = true;
    this.isEditMode = mode === 'edit';
    this.isConsultMode = mode === 'consult';
    this.currentCustomer = customer;

    if (customer) {
      this.customerForm.patchValue(customer);
    } else {
      this.customerForm.reset();
    }

    if (this.isConsultMode) {
      this.customerForm.get('idBusiness')?.clearValidators();
      this.customerForm.get('phone')?.clearValidators();
      this.customerForm.get('email')?.clearValidators();
      this.customerForm.get('startDate')?.setValidators(Validators.required);
      this.customerForm.get('endDate')?.setValidators(Validators.required);
    } else {
      this.customerForm.get('idBusiness')?.setValidators(Validators.required);
      this.customerForm.get('phone')?.setValidators(Validators.required);
      this.customerForm.get('email')?.setValidators([Validators.required, Validators.email]);
      this.customerForm.get('startDate')?.setValidators(Validators.required);
      this.customerForm.get('endDate')?.setValidators(Validators.required);
    }
    this.customerForm.updateValueAndValidity();
  }

  closeDialog(): void {
    this.display = false;
    this.customerForm.reset();
    this.isEditMode = false;
    this.isConsultMode = false;
  }

}
