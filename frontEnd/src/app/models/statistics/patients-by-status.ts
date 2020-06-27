export class PatientsByStatus {
  constructor(
    public activePatients?: number,
    public inactivePatients?: number,
    public totalPatients?: number
  ) { }
}
