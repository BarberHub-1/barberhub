export type ServiceId = 
  | 'CORTE_DE_CABELO'
  | 'BARBA'
  | 'SOBRANCELHA'
  | 'HIDRATACAO'
  | 'LUZES';

export const AVAILABLE_SERVICES: ServiceId[] = [
  'CORTE_DE_CABELO',
  'BARBA',
  'SOBRANCELHA',
  'HIDRATACAO',
  'LUZES'
];

export const getServiceLabel = (service: ServiceId): string => {
  const labels: Record<ServiceId, string> = {
    'CORTE_DE_CABELO': 'Corte de Cabelo',
    'BARBA': 'Barba',
    'SOBRANCELHA': 'Sobrancelha',
    'HIDRATACAO': 'Hidratação',
    'LUZES': 'Luzes'
  };
  return labels[service];
}; 