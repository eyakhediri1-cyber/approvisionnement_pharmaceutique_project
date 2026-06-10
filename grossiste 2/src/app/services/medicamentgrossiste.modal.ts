export interface MedicamentGrossiste{
  id_med: number;
  nom_med: string;
  prix_unitaire: number; 
  stock_dispo: number;
  seuil_alerte: number; 
  date_peremption: string;
}