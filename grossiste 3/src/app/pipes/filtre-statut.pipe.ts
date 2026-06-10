import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'filtreStatut', standalone: true })
export class FiltreStatutPipe implements PipeTransform {
  transform(commandes: any[], statut: string): number {
    if (!commandes) return 0;
    return commandes.filter(c => c.statut === statut).length;
  }
}