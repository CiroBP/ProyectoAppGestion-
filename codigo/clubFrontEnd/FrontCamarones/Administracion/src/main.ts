import { BrowserModule, bootstrapApplication } from '@angular/platform-browser';
import { HomeComponent } from './app/component/pages/home/home.component';
import { provideRouter } from '@angular/router';
import{routes} from './app/app-routing.module';
import { provideAnimations } from '@angular/platform-browser/animations'
import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule, provideHttpClient, withInterceptorsFromDi } from '@angular/common/http';
import { socioService } from './app/services/socio.service';
import { enableProdMode, importProvidersFrom } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { NgOptimizedImage } from '@angular/common';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';






bootstrapApplication(HomeComponent,{
    providers: [
        importProvidersFrom(BrowserModule,ReactiveFormsModule,NgOptimizedImage),
        provideRouter(routes),
        provideAnimations(),
        provideHttpClient(withInterceptorsFromDi()),
        socioService, provideAnimationsAsync()

]
})
.catch(err=> console.error(err))